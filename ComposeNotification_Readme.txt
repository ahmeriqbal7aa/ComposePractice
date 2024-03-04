ComposeNotification (FCM) - Used the Postman for Testing FCM

// =========== NOTE =========== //

Authorization : key = <server_key>		// AAAABUMnd6g:APA91bHwt0HgKd3LRzNarV6oa8D21D94zf_93WkaiiUU1vsRSgNn3GuH1XN0GfjbPJvjV0wA21OlXjjzDxb74Z8jMcXzGvA__QhL811ja1VBpA189XX5BykzgiR2joFDnx2pmKNCV-O2
Content-Type : application/json

// =========== NOTE =========== //

{
 "to" : "FCM_TOKEN_OR_TOPIC_WILL_BE_HERE",
 "notification" : {
     "body" : "Body of Your Notification",
     "title": "Title of Your Notification"
 }
}

// ============================ //

{
 "to" : "FCM_TOKEN_OR_TOPIC_WILL_BE_HERE",
 "data" : {
     "body" : "Body of Your Notification",
     "title": "Title of Your Notification",
	 "key_1" : "Value for key_1",
     "key_2" : "Value for key_2"
 }
}

// ====================================== Example ==================================== //

{
 //"to": "fDcjGiVOSL-5qZFpeK2zpu:APA91bGvx-7ORR7bW1twqB2JTdAovwjtggZgGIw01n_db4VvG32ei9PEqLvnVM43WIFACn-dm99L56p1A-7FW75W-EDF2ejbcravN8lJq3bu2f_LHsfiqK21c4DJv_DCOpu6v1AuTN5B",

"to":"/topics/All",

// "to":"/topics/News",

//   "notification": {
//     "body": "Notification assigned",
//     "title": "hello notify"
//   },

  "data": {
    "title": "hello data",
    "body": "Data assigned",
    "page" : "productDetail"
  }
}