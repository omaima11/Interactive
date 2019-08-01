package com.example.omaima.interactive_class;

public class VideoUpload {

        String name, url;

        public VideoUpload(){}

        public String getName(){
            return name;
        }

        public String getUrl() {
            return url;
        }

        public VideoUpload(String name, String url){
            this.name = name;
            this.url = url;
        }
}