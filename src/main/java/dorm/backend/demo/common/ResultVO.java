package dorm.backend.demo.common;

public class ResultVO {
    private String code;
    private Object data;
    private String msg;

    public static ResultVO success() {
        ResultVO result = new ResultVO();
        result.setCode("200");
        result.setMsg("请求成功");
        return result;
    }

    public static ResultVO success(Object data) {
        ResultVO result = new ResultVO();
        result.setCode("200");
        result.setData(data);
        return result;
    }

    public static ResultVO error(String code, String msg) {
        ResultVO result = new ResultVO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResultVO error(String msg) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }

    public static ResultVO authError() {
        ResultVO result = new ResultVO();
        result.setCode("401");
        result.setData("Unauthorized");
        result.setMsg("Invalid token");
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
