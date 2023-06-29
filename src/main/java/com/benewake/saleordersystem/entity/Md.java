package com.benewake.saleordersystem.entity;

import jnr.ffi.annotations.In;
import lombok.Data;

/**
 * @author Lcs
 * @CreateTime 2023年06月27 17:51
 * @Description TODO
 */
@Data
public class Md {
    private Integer FmaterialId;
    private String FNumber;
    private String FName;
    private Integer FCreateOrgId;
    private Integer FUseOrgId;
}
