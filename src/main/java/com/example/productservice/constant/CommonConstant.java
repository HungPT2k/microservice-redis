package com.example.productservice.constant;

public class CommonConstant {
    public static class Code {
        public static final String SUCCESS = "00";
        public static final String LOGIN_INVALID = "01";
        public static final String USER_INACTIVE = "02";
        public static final String NOT_FOUND = "03";
        public static final String ALREADY_EXISTS = "04";
        public static final String PERMISSION_DENIED = "05";
    }

    public static class Http {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
        public static final String UPDATE = "UPDATE";
    }

    public static class Method {
        public static final String FINDBYID = "findById";
        public static final String CREATEPRODUCT = "createProduct";
        public static final String UPDATEPRODUCT = "updateProduct";

    }
}
