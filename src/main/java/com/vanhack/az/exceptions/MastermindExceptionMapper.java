package com.vanhack.az.exceptions;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Maps exceptions to HTTP responses code
 * 
 * @author Igor K. Shiohara
 *
 */
public class MastermindExceptionMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MastermindExceptionMapper.class);
	
	@Provider
    @Singleton
    public static class MastermindValidationExceptionMapper implements ExceptionMapper<MastermindValidationException> {

        @Override
        public Response toResponse(MastermindValidationException exception) {
            
            checkNotNull(exception);
            
            LOGGER.error(exception.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }
	
	@Provider
    @Singleton
    public static class MastermindIllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

        @Override
        public Response toResponse(IllegalArgumentException exception) {
            
            checkNotNull(exception);
            
            LOGGER.error(exception.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }
	
	@Provider
    @Singleton
    public static class MastermindGeneralExceptionMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            
            checkNotNull(exception);
            
            LOGGER.error(exception.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }

}
