package com.shopping.pay.mapper;



import com.shopping.api.pay.entities.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoExtendMapper {

    int insertAndselectKey(PaymentInfo record);

}