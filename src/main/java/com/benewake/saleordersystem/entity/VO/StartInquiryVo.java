package com.benewake.saleordersystem.entity.VO;

import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月16 13:56
 * 描 述： TODO
 */
@Data
public class StartInquiryVo {
    private Integer inquirySeq;
    private Long inquiryId;
    private String itemCode;
    private Integer saleNum;
    private Integer itemType;
}
