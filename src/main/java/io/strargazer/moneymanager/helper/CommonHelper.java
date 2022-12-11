package io.strargazer.moneymanager.helper;

import io.strargazer.moneymanager.base.BaseEntity;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;

public class CommonHelper {

    private CommonHelper() {
    }

    public static String[] decodeBasicAuth(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        return credentials.split(":", 2);
    }

    public static void initBaseEntity(BaseEntity baseEntity) {
        baseEntity.setCreatedAt(Timestamp.from(Instant.now()));
        baseEntity.setCreatedBy("SYSTEM");
        baseEntity.setUpdatedBy("SYSTEM");
        baseEntity.setUpdatedAt(Timestamp.from(Instant.now()));
    }

}
