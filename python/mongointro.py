from pymongo import MongoClient
client = MongoClient("localhost")
db = client["NoSQLIntro"]

collection = db["PhoneNumbers"]

print(list(collection.find()))
