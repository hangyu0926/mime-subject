package cn.nanmi.msts.web.core;

import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.utils.JsonUtil;

import java.io.Serializable;

/**
 * 结果包装类<br>
 * 包装接口返回结果
 *
 * @author Vic Ding
 * @version [版本号, 2016年1月7日]
 */
public class ResultBean implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -6010932515432454307L;

    /**
     * 结果编码
     */
    private String code;

    /**
     * 结果信息
     */
    private String desc;

    /**
     * 返回结果
     */
    private Serializable content;

    /**
     * 无参构造
     */
    public ResultBean() {
    }

    /**
     * @param code
     */
    public ResultBean(ErrorCode code) {
        this.code = code.getErrorCode();
        this.desc = code.getErrorDesc();
    }


    /**
     * @param code
     * @param content
     */
    public ResultBean(ErrorCode code, Serializable content) {
        this.code = code.getErrorCode();
        this.desc = code.getErrorDesc();
        this.content = content;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the content
     */
    public Serializable getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(Serializable content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JsonUtil.beanToJson(this);
    }
}
