/**
 * null
 */
package edu.nyu.cs9223.project.model;

import javax.annotation.Generated;

/**
 *
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class InternalServerErrorException extends ChatBotServiceException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    /**
     * Constructs a new InternalServerErrorException with the specified error message.
     *
     * @param message Describes the error encountered.
     */
    public InternalServerErrorException(String message) {
        super(message);
    }

    /**
     * @return
     */

    @com.fasterxml.jackson.annotation.JsonProperty("code")
    public Integer getCode() {
        return this.code;
    }

    /**
     * @param code
     */

    @com.fasterxml.jackson.annotation.JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @param code
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public InternalServerErrorException code(Integer code) {
        setCode(code);
        return this;
    }

}
