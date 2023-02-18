package com.thevirtugroup.postitnote.exception;



public class ApiRequestException extends RuntimeException
{

    public ApiRequestException(String message ){
     super(message);
   }

   /*
    public ApiRequestException(String message, Exception e ){
        super(message, e);
    }

    */

}
