package com.RESTfullCRUD.BasicCRUD.constant;

public class PathConstant {

    /* products ------------------------------------------------------------------*/
    public static final String ADD_PRODUCT = "/addProduct";
    public static final String GET_PRODUCT = "/getProduct";
    public static final String GET_PRODUCT_BY_ID = GET_PRODUCT+"/{id}";
    public static final String GET_PRODUCT_BY_NAME = GET_PRODUCT+"/name/{name}";
    public static final String UPDATE_PRODUCT = "/updateProduct/{id}";
    public static final String REMOVE_PRODUCT = "/removeProduct/{id}";
    /* users ---------------------------------------------------------------------*/
    public static final String REGISTRATION = "/registration";
    public static final String LOGIN = "/login";
    public static final String GET_USER = "/getUser";
    public static final String GET_USER_ID = GET_USER+"/{id}";
    public static final String GET_USERDTO = "/getUserDTO";
    public static final String UPDATE_USER = "/updateUser/{id}";
    public static final String ADD_ROLE_TO_USER = "/addRoleToUser/{id}";
    public static final String REMOVE_USER = "/removeUser/{id}";
    public static final String GET_USER_BY_COUNTRY = "/getUserByCountry/{country}";

    /* Role */
    public static final String GET_ROLE= "/roles";
    public static final String ADD_ROLE= "/addRole";
    public static final String UPDATE_ROLE= "/updateRole/{id}";


    public static final String GET_SESSION_DETAILS = "/getSessionDetails";
    public static final String GET_CLAIMS_DETAILS = "/getClaimsDetails";
}
