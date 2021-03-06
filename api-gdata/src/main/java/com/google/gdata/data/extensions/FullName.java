/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.data.extensions;

import com.google.gdata.data.AbstractExtension;
import com.google.gdata.data.AttributeGenerator;
import com.google.gdata.data.AttributeHelper;
import com.google.gdata.data.ExtensionDescription;
import com.google.gdata.util.Namespaces;
import com.google.gdata.util.ParseException;

/**
 * Person's full, formatted name.
 *
 * 
 */
@ExtensionDescription.Default(
    nsAlias = Namespaces.gAlias,
    nsUri = Namespaces.g,
    localName = FullName.XML_NAME)
public class FullName extends AbstractExtension {

  /** XML element name */
  static final String XML_NAME = "fullName";

  /** XML "yomi" attribute name */
  private static final String YOMI = "yomi";

  /** Value */
  private String value = null;

  /** Pronunciation help */
  private String yomi = null;

  /**
   * Default mutable constructor.
   */
  public FullName() {
    super();
  }

  /**
   * Immutable constructor.
   *
   * @param value value.
   * @param yomi pronunciation help.
   */
  public FullName(String value, String yomi) {
    super();
    setValue(value);
    setYomi(yomi);
    setImmutable(true);
  }

  /**
   * Returns the value.
   *
   * @return value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value value or <code>null</code> to reset
   */
  public void setValue(String value) {
    throwExceptionIfImmutable();
    this.value = value;
  }

  /**
   * Returns whether it has the value.
   *
   * @return whether it has the value
   */
  public boolean hasValue() {
    return getValue() != null;
  }

  /**
   * Returns the pronunciation help.
   *
   * @return pronunciation help
   */
  public String getYomi() {
    return yomi;
  }

  /**
   * Sets the pronunciation help.
   *
   * @param yomi pronunciation help or <code>null</code> to reset
   */
  public void setYomi(String yomi) {
    throwExceptionIfImmutable();
    this.yomi = yomi;
  }

  /**
   * Returns whether it has the pronunciation help.
   *
   * @return whether it has the pronunciation help
   */
  public boolean hasYomi() {
    return getYomi() != null;
  }

  @Override
  protected void validate() {
    if (value == null) {
      throw new IllegalStateException("Missing text content");
    }
  }

  /**
   * Returns the extension description, specifying whether it is required, and
   * whether it is repeatable.
   *
   * @param required   whether it is required
   * @param repeatable whether it is repeatable
   * @return extension description
   */
  public static ExtensionDescription getDefaultDescription(boolean required,
      boolean repeatable) {
    ExtensionDescription desc =
        ExtensionDescription.getDefaultDescription(FullName.class);
    desc.setRequired(required);
    desc.setRepeatable(repeatable);
    return desc;
  }

  @Override
  protected void putAttributes(AttributeGenerator generator) {
    generator.setContent(value);
    generator.put(YOMI, yomi);
  }

  @Override
  protected void consumeAttributes(AttributeHelper helper) throws ParseException
      {
    value = helper.consume(null, true);
    yomi = helper.consume(YOMI, false);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!sameClassAs(obj)) {
      return false;
    }
    FullName other = (FullName) obj;
    return eq(value, other.value)
        && eq(yomi, other.yomi);
  }

  @Override
  public int hashCode() {
    int result = getClass().hashCode();
    if (value != null) {
      result = 37 * result + value.hashCode();
    }
    if (yomi != null) {
      result = 37 * result + yomi.hashCode();
    }
    return result;
  }

  @Override
  public String toString() {
    return "{FullName value=" + value + " yomi=" + yomi + "}";
  }

}
