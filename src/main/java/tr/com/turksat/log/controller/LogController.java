package tr.com.turksat.log.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import tr.com.turksat.log.domain.entity.WebServiceBaseLog;
import tr.com.turksat.log.domain.payload.LogRequest;
import tr.com.turksat.log.domain.payload.LogResponse;
import tr.com.turksat.log.service.LogService;

/**
 * @author scinkir
 * @since 25.11.2024
 */
@Path("/logs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogController {

    @Inject
    LogService logService;

    @POST
    @Operation(summary = "Create a new log entry",
            description = "Creates a new log entry with the provided details")
    @APIResponses(value = {
            @APIResponse(responseCode = "200",
                    description = "Log entry created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogResponse.class))),
            @APIResponse(responseCode = "400",
                    description = "Invalid request payload"),
            @APIResponse(responseCode = "500",
                    description = "Internal server error")
    })
    public Response createLog(
            @Valid @NotNull(message = "Request body cannot be null")
            LogRequest request) {
        try {
            request.validate();
            WebServiceBaseLog log = logService.createLog(request);
            return Response.ok(LogResponse.success(Long.valueOf(log.getId()))).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(LogResponse.error(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(LogResponse.error("Error creating log entry: " + e.getMessage()))
                    .build();
        }
    }
}
