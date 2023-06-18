package com.newshop.shopnetnew.mapper;

import com.newshop.shopnetnew.domain.DeliveryPoints;
import com.newshop.shopnetnew.dto.PointDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PointMapper {
    PointMapper MAPPER = Mappers.getMapper(PointMapper.class);
    DeliveryPoints toPoint(PointDTO dto);
    List<PointDTO> fromPoint(List<DeliveryPoints> points);
    List<DeliveryPoints> toPointList(List<PointDTO> pointDTOS);
    List<PointDTO> formPointList(List<DeliveryPoints> points);
}
