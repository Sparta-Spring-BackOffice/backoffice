package com.example.backoffice.product.dto;

import com.example.backoffice.product.consts.ProductStatus;
import lombok.Getter;

@Getter
public class UpdateProductStatusRequest {
    private ProductStatus status;

}
