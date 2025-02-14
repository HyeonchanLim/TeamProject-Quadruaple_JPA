package com.green.project_quadruaple.trip.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class DeleteTripUserReq {

    @JsonProperty("trip_id")
    private final long tripId;

    @JsonProperty("leader_id")
    private final long leaderId;

    @JsonProperty("target_user_id")
    private final long targetUserId;
}
