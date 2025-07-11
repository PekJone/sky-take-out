package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-07  10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String openid;

    private String name ;

    private String phone;

    private String sex;

    private String idNumber ;
    private String avatar;

    private LocalDateTime createTime;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", openid='").append(openid).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
