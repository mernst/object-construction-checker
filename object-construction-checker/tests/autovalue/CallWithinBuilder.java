import com.google.auto.value.AutoValue;
import org.checkerframework.checker.objectconstruction.qual.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

@AutoValue
abstract class CallWithinBuilder {

  public abstract ImmutableList<String> names();

  static Builder builder() {
    return new AutoValue_CallWithinBuilder.Builder();
  }

  @AutoValue.Builder
  abstract static class Builder {

    abstract ImmutableList.Builder<String> namesBuilder();

    public Builder addName(String name) {
      namesBuilder().add(name);
      return this;
    }

    public Builder addNames(Collection<String> names) {
      for (String n: names) {
        addName(n);
      }
      return this;
    }

    abstract CallWithinBuilder build();

  }


}