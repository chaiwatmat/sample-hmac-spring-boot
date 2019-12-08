package co.chaiwatm.demo.models;

import lombok.*;

@Data
public class RequestHeaderModel {
    private String requestuid;
    private String authorization;
    private String useragent;
    private String accept;
    private String host;
    private String cookie;
    private String connection;
    private String auth1;
    private String auth2;
}