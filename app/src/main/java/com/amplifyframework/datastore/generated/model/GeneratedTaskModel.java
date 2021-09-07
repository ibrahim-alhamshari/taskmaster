package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the GeneratedTaskModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "GeneratedTaskModels")
public final class GeneratedTaskModel implements Model {
  public static final QueryField ID = field("GeneratedTaskModel", "id");
  public static final QueryField TASK_NAME = field("GeneratedTaskModel", "taskName");
  public static final QueryField BODY = field("GeneratedTaskModel", "body");
  public static final QueryField STATE = field("GeneratedTaskModel", "state");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String taskName;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="String") String state;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTaskName() {
      return taskName;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getState() {
      return state;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private GeneratedTaskModel(String id, String taskName, String body, String state) {
    this.id = id;
    this.taskName = taskName;
    this.body = body;
    this.state = state;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      GeneratedTaskModel generatedTaskModel = (GeneratedTaskModel) obj;
      return ObjectsCompat.equals(getId(), generatedTaskModel.getId()) &&
              ObjectsCompat.equals(getTaskName(), generatedTaskModel.getTaskName()) &&
              ObjectsCompat.equals(getBody(), generatedTaskModel.getBody()) &&
              ObjectsCompat.equals(getState(), generatedTaskModel.getState()) &&
              ObjectsCompat.equals(getCreatedAt(), generatedTaskModel.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), generatedTaskModel.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTaskName())
      .append(getBody())
      .append(getState())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("GeneratedTaskModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("taskName=" + String.valueOf(getTaskName()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TaskNameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static GeneratedTaskModel justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new GeneratedTaskModel(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      taskName,
      body,
      state);
  }
  public interface TaskNameStep {
    BuildStep taskName(String taskName);
  }
  

  public interface BuildStep {
    GeneratedTaskModel build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep body(String body);
    BuildStep state(String state);
  }
  

  public static class Builder implements TaskNameStep, BuildStep {
    private String id;
    private String taskName;
    private String body;
    private String state;
    @Override
     public GeneratedTaskModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new GeneratedTaskModel(
          id,
          taskName,
          body,
          state);
    }

      public String getId() {
          return id;
      }

      public void setId(String id) {
          this.id = id;
      }

      public String getTaskName() {
          return taskName;
      }

      public void setTaskName(String taskName) {
          this.taskName = taskName;
      }

      public String getBody() {
          return body;
      }

      public void setBody(String body) {
          this.body = body;
      }

      public String getState() {
          return state;
      }

      public void setState(String state) {
          this.state = state;
      }

      @Override
     public BuildStep taskName(String taskName) {
        Objects.requireNonNull(taskName);
        this.taskName = taskName;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep state(String state) {
        this.state = state;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String taskName, String body, String state) {
      super.id(id);
      super.taskName(taskName)
        .body(body)
        .state(state);
    }
    
    @Override
     public CopyOfBuilder taskName(String taskName) {
      return (CopyOfBuilder) super.taskName(taskName);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder state(String state) {
      return (CopyOfBuilder) super.state(state);
    }
  }


  
}
