package org.dromara.okx.domain.vo;

import javax.annotation.processing.Generated;
import org.dromara.okx.domain.CopytradingInfo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T10:03:35+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class CopytradingInfoVoToCopytradingInfoMapperImpl implements CopytradingInfoVoToCopytradingInfoMapper {

    @Override
    public CopytradingInfo convert(CopytradingInfoVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        CopytradingInfo copytradingInfo = new CopytradingInfo();

        copytradingInfo.setId( arg0.getId() );
        copytradingInfo.setName( arg0.getName() );
        copytradingInfo.setUniqueCode( arg0.getUniqueCode() );
        copytradingInfo.setRecomIndex( arg0.getRecomIndex() );
        copytradingInfo.setPlatform( arg0.getPlatform() );

        return copytradingInfo;
    }

    @Override
    public CopytradingInfo convert(CopytradingInfoVo source, CopytradingInfo target) {
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
