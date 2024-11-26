package tr.com.turksat.log.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tr.com.turksat.log.dao.data.LogSearchCriteria;
import tr.com.turksat.log.domain.entity.WebServiceBaseLog;
import tr.com.turksat.log.domain.payload.LogRequest;
import tr.com.turksat.log.service.LogService;

import java.util.List;

/**
 * @author scinkir
 * @since 25.11.2024
 */
@Path("/logs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {

    @Inject
    LogService logService;

    @POST
    @Path("/search")
    public Response searchLogs(LogSearchCriteria criteria) {
        List<WebServiceBaseLog> logs = logService.searchLogs(criteria);
        return Response.ok(logs).build();
    }


    @POST
    public Response createLog(LogRequest request) {
        WebServiceBaseLog log = logService.createLog(request);
        return Response.ok(log).build();
    }
}
