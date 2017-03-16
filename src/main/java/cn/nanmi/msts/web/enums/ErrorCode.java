package cn.nanmi.msts.web.enums;

/**
 * 错误码枚举<br> 
 * 错误码枚举
 *
 * @author Vic Ding
 * @version [V1.0, 2016年6月6日]
 */
public enum ErrorCode {
    SUCCESS("0000","成功"),
    /**
     * 重定向error
     */
    REDIRECT_LOGIN("901","登录信息异常，请重新登录"),
    REDIRECT_INDEX("902","您已登录，跳转到主页"),
    REDIRECT_DISABLE("903","用户账号被禁用"),
    REDIRECT_KICKOUT("904","用户账号已在别处登录，请重新登录"),
    WX_REDIRECT_LOGIN("910","登录信息异常，请重新登录"),

    /**
     * 系统默认error
     */
    SYSTEM_UNKNOW_ERROR("1001","未知异常"),
    ILLEGAL_NULL_PARAM("1002", "参数不能为空"),
    PARAM_NOT_SAME("1003", "入参类型不一致"),
    RETRUN_DATA_NULL("1004","数据为空或者null"),
    FAIL_NOT_LOGIN ("9001", "您还没有登录"),
    FAIL_INVALID_PARAMS("9002", "参数非法"),

    /**
     * 用户相关 90**
     */
    //9002-用户名或者密码错误
    CONFIRM_PASSWORD_WRONG("9003","用户名或者密码错误"),
    // 9003- 用户不存在
    USER_NOT_EXIST("9004","用户不存在"),
    // 9004-密码为空
    USERNAME_PASSWORD_NULL("9005","请填写完成"),
    // 9005-未知错误
    UNKNOWN_WRROR("9006","未知错误"),
    //9006-密码错误
    PASSWORD_WRONG("9007","旧密码错误，请重新输入"),
    // 9007-新密码与旧密码不能相同
    SAME_PASSWORD("9008","新密码与旧密码不能相同"),
    //9008-新密码与确认密码不一致
    DOUBLE_PASSWORD_ERROR("9009","新密码与确认密码不一致"),
    PASSWORD_FORMAT_ERROR("9010","密码不符合要求，请重新输入"),


    //9011 - session 超时
    SESSION_ERROR("9011","用户信息获取失败，请登录后再试"),
    SESSION_SAME_ERROR("9012","非法登录请求，请刷新该页面或登出另一个账号后重试"),
    SESSION_REPEAT_ERROR("9013","非法登录请求，请先登出当前账号"),
    STATUS_ERROR("9014","修改状态无效，与原状态相同"),
    USER_IS_DISABLE("9015","该账号已经被禁用，请联系管理员"),
    USER_IS_INIT("9016","登录成功，您是第一次登录该系统，请立即修改密码！"),


    WX_EXIST("9021","您的微信号已经绑定其他账号或重复存在，请联系管理员"),
    WX_AUTH_ERROR("9020","微信授权失败"),
    PC_PERMISSION_ERROR("9030","您没有权限执行该操作！"),
    /**
     * 角色
     */
    SERVER_DATA_ERROR("1002", "服务器数据异常"),
    /**
     * 用户管理
     */
    ADD_FAILED("8001", "添加失败"),
    ADD_EMAIL_FAILED("8002", "邮箱格式有误"),
    ADD_PWD_FAILED("8003", "密码格式有误"),
    ADD_WORKID_EXIST_FAILED("8004", "对不起，该工号已经存在"),
    ADD_EMAIL_EXIST_FAILED("8005", "对不起，该邮箱已经存在"),
    PERMISSION_ERROR("8006", "不能修改非超级管理员为超级管理员"),
    UPDATE_STATUS_ERROR("8007", "该用户未登录，不能修改状态"),

    ADD_MOBILE_EXIST_FAILED("8010", "手机号已存在"),
    ADD_MAIL_EXIST_FAILED("8011", "邮箱已存在"),
    ADD_NOT_ALL_FAILED("8012", "信息不全"),
    ADD_FORMAT_ERROR_FAILED("8013", "格式错误"),

    USER_ID_IS_NULL("7001","用户ID不能为空"),
    USER_ID_NOT_HAVE_PERMISSION("7002","用户没有权限"),
    NO_MAPPING_WORKDATA("7003","无对应数据"),
    WORKDATA_EXCEL_EXPORT_FAIL("7004","工作量表文件导出失败"),
	
	/**
	 * 权限模块
	 */
    PERMISSION_IN_USER("6001","该权限被角色占用，请先修改角色权限,再来删除"),
    PERMISSION_NOT_EXSIT("6002","权限不存在"),
	PERMISSION_ISNOTDEFAULT("6003","默认权限不可修改"),
	PERMISSION_ISNOTDELETE("6004","默认权限不可修改"),
	
	PARAMETER_IS_NULL("8000","获取不到请求参数"),
	PROJECT_OPERATE_SUCCESS("8001","操作成功"),
	PROJECT_OPERATE_FAIL("8002","操作失败"),
	UPLOAD_SUCCESS("8003","上传成功"),
	UPLOAD_FAIL("8004","上传失败"),
	PICTURE_TYPE_FAIL("8005","图片格式错误"),
	ATTACHMENT_TYPE_FAIL("8006","附件格式错误"),
	DEL_FILE_SUCCESS("8007","删除文件成功"),
	DEL_FILE_FAIL("8008","删除文件失败"),
	PICTURE_IS_NULL("8009","请选择图片上传"),
	ATTACHMENT_IS_NULL("8010","请选择附件上传"),
	ATTACHMENT_SIZE_ZERO("8011","空文件"),
	UPLOAD_BIG_FAIL("8012","文件上传限制为500M以内"),
	SEND_EMAIL_IS_NULL("8013","不存在该文件"),
	SEND_FAIL("8014","发送失败"),
    WEI_XIN_ACCESS_TOKEN_INVALID("8015","微信推送失败，请重试"),
    NO_PERMISSION ("8016","对不起，没有相应权限"),
    USER_GROUP_IS_NULL("8017","您选中的用户组推送人数为空"),
    /**
     * 项目相关错误码50*
     */
    NOT_FIND_PROJECTID("5001","没有找到该项目信息"),
    PROJECT_APPRAISAL_TOO_LONG("5002","输入的评价内容过长，请少于100个汉字");

	
    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    ErrorCode(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
