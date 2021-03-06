package org.checkerframework.checker.objectconstruction;

import java.util.LinkedHashSet;
import java.util.Properties;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.returnsreceiver.ReturnsReceiverChecker;
import org.checkerframework.common.value.ValueChecker;
import org.checkerframework.framework.source.SupportedOptions;
import org.checkerframework.framework.source.SuppressWarningsPrefix;

/**
 * The primary typechecker for the object construction checker, which allows programmers to specify
 * unsafe combinations of options to builder or builder-like interfaces and prevent dangerous
 * objects from being instantiated.
 */
@SuppressWarningsPrefix({"builder", "object.construction", "objectconstruction"})
@SupportedOptions({
  ObjectConstructionChecker.USE_VALUE_CHECKER,
  ObjectConstructionChecker.COUNT_FRAMEWORK_BUILD_CALLS,
  ObjectConstructionChecker.DISABLED_FRAMEWORK_SUPPORTS,
})
public class ObjectConstructionChecker extends BaseTypeChecker {

  public static final String USE_VALUE_CHECKER = "useValueChecker";

  public static final String COUNT_FRAMEWORK_BUILD_CALLS = "countFrameworkBuildCalls";

  public static final String DISABLED_FRAMEWORK_SUPPORTS = "disableFrameworkSupports";

  public static final String LOMBOK_SUPPORT = "LOMBOK";

  public static final String AUTOVALUE_SUPPORT = "AUTOVALUE";

  @Override
  protected LinkedHashSet<Class<? extends BaseTypeChecker>> getImmediateSubcheckerClasses() {
    LinkedHashSet<Class<? extends BaseTypeChecker>> checkers =
        super.getImmediateSubcheckerClasses();
    checkers.add(ReturnsReceiverChecker.class);

    // BaseTypeChecker#hasOption calls this method (so that all subcheckers' options are
    // considered),
    // so the processingEnvironment must be checked for the option directly.
    if (this.processingEnv.getOptions().containsKey(USE_VALUE_CHECKER)) {
      checkers.add(ValueChecker.class);
    }
    return checkers;
  }

  /**
   * Overridden because the messages.properties file isn't being loaded, for some reason. I think it
   * has to do with relative paths? For whatever reason, this has to be hardcoded into the checker
   * itself here for checkers that aren't part of the CF itself.
   */
  @Override
  public Properties getMessagesProperties() {
    Properties messages = super.getMessagesProperties();
    messages.setProperty(
        "finalizer.invocation.invalid",
        "This finalizer cannot be invoked, because the following methods have not been called: %s\n");
    messages.setProperty(
        "predicate.invalid",
        "An unparseable predicate was found in an annotation. Predicates must be produced by this grammar: S --> method name | (S) | S && S | S || S. The message from the evaluator was: %s \\n");
    return messages;
  }

  int numBuildCalls = 0;

  @Override
  public void typeProcessingOver() {
    if (getBooleanOption(COUNT_FRAMEWORK_BUILD_CALLS)) {
      System.out.printf("Found %d build() method calls.\n", numBuildCalls);
    }
    super.typeProcessingOver();
  }
}
