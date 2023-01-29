package com.example.whyhmm.domain.utils

object Constants {
    val BASE_URL = "https://supermanagerapi.taskmo.in/"

    val BASE_URL_FieldOn = "https://amazon.auth.api.taskmo.co/api/"
    var PREF_NAME = "TASKMOTL"
    var TOKEN = "TOKEN"
    var ASM_INFO = "ASM_INFO"
    var ASM_ID = "ASM_ID"
    var USER_TYPE = "USER_TYPE"
    var PHN_NUMBER = "PHN_NUMBER"
    var ONBOARD_STATUS = "ONBOARD_STATUS"
    var WALK_STATUS = "YES"
    var PROJECT_STATUS = "PROJECT_STATUS"
    var INTIAL_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJpYXQiOjE2MDcxNTEwOTF9.ioWcBfTlcUXK-80EEL_TaYDi24xEdT4aE1x0VZ1tU5"
    //    -----------------Temp------------------------
    const val S3_IDENTITY_POOL_ID_URL = "ap-south-1:e0f7452d-1478-402f-a9ed-8a7fbb6cffd1"
    const val S3_BUCKET_ID = "taskmotech1b" // for production
    //    public static final String S3_BUCKET_ID = "taskmotech1a";// for testing
    var BASE_AWS_FILE_URL = "https://$S3_BUCKET_ID.s3.amazonaws.com/"
}