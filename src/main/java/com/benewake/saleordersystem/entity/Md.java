package com.benewake.saleordersystem.entity;

import lombok.Data;

/**
 * @author Lcs
 * @CreateTime 2023年06月27 17:51
 * @Description TODO
 */
@Data
public class Md {
    private Integer fMaterialId;
    private String fNumber;
    private String fName;
    private Integer fCreateOrgId;
    private Integer fUseOrgId;
}
