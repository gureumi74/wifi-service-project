package wifiService.domain.bookmark;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Bookmark {
    private Integer id;
    private Timestamp createdAt;
    private Integer groupId;
    private Integer wifiInfoId;
}
