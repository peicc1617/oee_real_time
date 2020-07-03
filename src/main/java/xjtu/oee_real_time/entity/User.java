package xjtu.oee_real_time.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类，对应数据库字段
 */
@Data
public class User implements Serializable{
    /**
     * 用户ID
     */
    private long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 组织
     */
    private String domain;

    private String permission;

    /**
     * 电子邮箱
     */
//    @Email
    private String email;

    /**
     * 电话
     */
    private String phoneNumber;

    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 组织
     */
    private String domainName;
    private String userPhoto;//用户头像
}