package httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * parses http req from i/p stream
 * format:
 * ReqLine - METHOD PATH HTTP/VERSION
 * Headers: Key: val
 *
 * Body
 */
public class HttpRequestParser {
     public static HttpRequest parse(InputStream input) throws IOException {

         /**
          * gets user inp. InputStreamReader deals with raw bytes, converts bytes to characters
          * uses UTF-8 encoding so bytes are interpreted correctly
          * BufferedReader wraps reader with a buffer, as in, reads in chunks instead of 1 char at a time
          */
         BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
         String reqLine = reader.readLine();

         //parses METHOD PATH HTTP/VERSION

         //reads 1 line from i/p, checks if empty
         String requestLine = reader.readLine();
         if(requestLine == null || requestLine.isEmpty()) {
             throw new IOException("Empty request");
         }

         //1st line parsed
         String[] parts = requestLine.split(" ");
         if(parts.length != 3) {
             throw new IOException("Invalid request line: " + requestLine);
         }

         String method = parts[0];
         String path = parts[1];
         String version = parts[2];

         //parsing headers
         Map<String, String> headers = parseHeaders(reader);

         //parsing body
         String body = parseBody(reader, headers);

         return new HttpRequest(method, path, version, headers,  body);
     }

     private static Map<String, String> parseHeaders(BufferedReader reader) throws IOException{
         Map<String, String> headers = new HashMap<>();
         String line;

         while((line = reader.readLine()) != null && !line.isEmpty()) {
             int colonIndex = line.indexOf(":");
             if(colonIndex > 0) {
                 String key = line.substring(0, colonIndex).trim();
                 String value = line.substring(colonIndex + 1).trim();
                 headers.put(key.toLowerCase(), value);
             }
         }

         return headers;
     }

     private static String parseBody(BufferedReader reader,  Map<String, String> headers) throws IOException{

         //tries to find content-length header in the headers. body only exists if theres a content-length header
         //content-length tells how many bytes the body contains
         String contentLengthStr = headers.get("content-length");

         if(contentLengthStr != null) {
             try {

                 //allocates a char array the size of the body
                 int contentLength = Integer.parseInt(contentLengthStr);
                 char[] bodyChars = new char[contentLength];
                 //tracks how much has been read
                 int totalRead = 0;

                 while (totalRead < contentLength) {
                     int read = reader.read(bodyChars, totalRead, contentLength - totalRead);
                     if(read == -1) break;
                     totalRead += read;
                 }

                 return new String(bodyChars, 0, totalRead); //string constructor. source, offset, num of chars

             }
             catch(NumberFormatException e) {
                 throw new IOException("Invalid content-length" + contentLengthStr);
             }
         }

         //no content-length => no body
         return null;
     }
}