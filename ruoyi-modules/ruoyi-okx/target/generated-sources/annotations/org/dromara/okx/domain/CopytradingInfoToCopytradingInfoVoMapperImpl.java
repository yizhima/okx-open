package org.dromara.okx.domain;

import javax.annotation.processing.Generated;
import org.dromara.okx.domain.vo.CopytradingInfoVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T10:03:35+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class CopytradingInfoToCopytradingInfoVoMapperImpl implements CopytradingInfoToCopytradingInfoVoMapper {

    @Override
    public CopytradingInfoVo convert(CopytradingInfo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        CopytradingInfoVo copytradingInfoVo = new CopytradingInfoVo();

        copytradingInfoVo.setId( arg0.getId() );
        copytradingInfoVo.setName( arg0.getName() );
        copytradingInfoVo.setUniqueCode( arg0.getUniqueCode() );
        copytradingInfoVo.setRecomIndex( arg0.getRecomIndex() );
        copytradingInfoVo.setPlatform( arg0.getPlatform() );

        return copytradingInfoVo;
    }

    @Override
    public CopytradingInfoVo convert(CopytradingInfo source, CopytradingInfoVo target) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setName( source.getName() );
        target.setUniqueCode( source.getUniqueCode() );
        target.setRecomIndex( source.getRecomIndex() );
        target.setPlatform( source.getPlatform() );

        return target;
    }
}
