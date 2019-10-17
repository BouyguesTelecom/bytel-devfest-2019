import json
import boto3
import datetime
import tempfile
import time
import os
import uuid
from botocore.exceptions import ClientError

def lambda_handler(event, context):
    print("REQUEST RECEIVED: {}".format(json.dumps(event)))

    response = {}
    response['statusCode'] = 200

    id = event['uuid']

    if not os.path.isfile(tempfile.gettempdir() + '/' + id):
        filename = tempfile.gettempdir() + '/' + id
        open(filename, 'a').close()

        print("File {} created".format(filename))
        
        try:
            print("Dynamodb insert in progress...")
            dynamodb = boto3.client('dynamodb')
            dynamodb.put_item(
                    TableName = 'lambda-executions', 
                    Item = {
                    'uuid': { 'S': str(uuid.uuid1()) },
                    'id_session': { 'S': id },
                    'date': { 'S': str(datetime.datetime.now()) }
                    }
                )

            print("Dynamodb insert terminated")
            
            response['body'] = json.dumps({
                "message": "New lambda instantiated"
            })



        except ClientError as ex:
            error_message = "Dynamodb put item failed. Received client error: {}".format(ex)
            response['statusCode'] = 500
            response['error_message'] = error_message
            
            print(error_message)
    else:
        print("Reused lambda for uuid: {}".format(id))

    time.sleep(1)

    print("Completed lambda run")
    return response