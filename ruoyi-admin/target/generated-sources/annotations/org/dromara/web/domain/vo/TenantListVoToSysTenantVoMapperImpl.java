package org.dromara.web.domain.vo;

import javax.annotation.processing.Generated;
import org.dromara.system.domain.vo.SysTenantVo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T10:03:40+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class TenantListVoToSysTenantVoMapperImpl implements TenantListVoToSysTenantVoMapper {

    @Override
    public SysTenantVo convert(TenantListVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysTenantVo sysTenantVo = new SysTenantVo();

        sysTenantVo.setTenantId( arg0.getTenantId() );
        sysTenantVo.setCompanyName( arg0.getCompanyName() );
        sysTenantVo.setDomain( arg0.getDomain() );

        return sysTenantVo;
    }

    @Override
    public SysTenantVo convert(TenantListVo source, SysTenantVo target) {
        if ( source == null ) {
            return target;
        }

        target.setTenantId( source.getTenantId() );
        target.setCompanyName( source.getCompanyName() );
        target.setDomain( source.getDomain() );

        return target;
    }
}
