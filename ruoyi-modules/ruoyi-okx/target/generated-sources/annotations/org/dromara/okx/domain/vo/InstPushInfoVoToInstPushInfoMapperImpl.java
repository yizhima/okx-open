package org.dromara.okx.domain.vo;

import javax.annotation.processing.Generated;
import org.dromara.okx.domain.InstPushInfo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T10:03:35+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class InstPushInfoVoToInstPushInfoMapperImpl implements InstPushInfoVoToInstPushInfoMapper {

    @Override
    public InstPushInfo convert(InstPushInfoVo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        InstPushInfo instPushInfo = new InstPushInfo();

        instPushInfo.setId( arg0.getId() );
        instPushInfo.setName( arg0.getName() );
        instPushInfo.setInstId( arg0.getInstId() );
        instPushInfo.setInstType( arg0.getInstType() );

        return instPushInfo;
    }

    @Override
    public InstPushInfo convert(InstPushInfoVo source, InstPushInfo target) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setName( source.getName() );
        target.setInstId( source.getInstId() );
        target.setInstType( source.getInstType() );

        return target;
    }
}
