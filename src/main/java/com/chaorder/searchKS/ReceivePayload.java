/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.chaorder.searchKS;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-07-04")
public class ReceivePayload implements org.apache.thrift.TBase<ReceivePayload, ReceivePayload._Fields>, java.io.Serializable, Cloneable, Comparable<ReceivePayload> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReceivePayload");

  private static final org.apache.thrift.protocol.TField TRACE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("traceId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LANGUAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("language", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SETTING_VAR_FIELD_DESC = new org.apache.thrift.protocol.TField("settingVar", org.apache.thrift.protocol.TType.MAP, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ReceivePayloadStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ReceivePayloadTupleSchemeFactory();

  public java.lang.String traceId; // required
  public java.lang.String language; // required
  public java.lang.String content; // required
  public java.util.Map<java.lang.String,java.lang.String> settingVar; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRACE_ID((short)1, "traceId"),
    LANGUAGE((short)2, "language"),
    CONTENT((short)3, "content"),
    SETTING_VAR((short)4, "settingVar");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TRACE_ID
          return TRACE_ID;
        case 2: // LANGUAGE
          return LANGUAGE;
        case 3: // CONTENT
          return CONTENT;
        case 4: // SETTING_VAR
          return SETTING_VAR;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRACE_ID, new org.apache.thrift.meta_data.FieldMetaData("traceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LANGUAGE, new org.apache.thrift.meta_data.FieldMetaData("language", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SETTING_VAR, new org.apache.thrift.meta_data.FieldMetaData("settingVar", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReceivePayload.class, metaDataMap);
  }

  public ReceivePayload() {
  }

  public ReceivePayload(
    java.lang.String traceId,
    java.lang.String language,
    java.lang.String content,
    java.util.Map<java.lang.String,java.lang.String> settingVar)
  {
    this();
    this.traceId = traceId;
    this.language = language;
    this.content = content;
    this.settingVar = settingVar;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReceivePayload(ReceivePayload other) {
    if (other.isSetTraceId()) {
      this.traceId = other.traceId;
    }
    if (other.isSetLanguage()) {
      this.language = other.language;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetSettingVar()) {
      java.util.Map<java.lang.String,java.lang.String> __this__settingVar = new java.util.HashMap<java.lang.String,java.lang.String>(other.settingVar);
      this.settingVar = __this__settingVar;
    }
  }

  public ReceivePayload deepCopy() {
    return new ReceivePayload(this);
  }

  @Override
  public void clear() {
    this.traceId = null;
    this.language = null;
    this.content = null;
    this.settingVar = null;
  }

  public java.lang.String getTraceId() {
    return this.traceId;
  }

  public ReceivePayload setTraceId(java.lang.String traceId) {
    this.traceId = traceId;
    return this;
  }

  public void unsetTraceId() {
    this.traceId = null;
  }

  /** Returns true if field traceId is set (has been assigned a value) and false otherwise */
  public boolean isSetTraceId() {
    return this.traceId != null;
  }

  public void setTraceIdIsSet(boolean value) {
    if (!value) {
      this.traceId = null;
    }
  }

  public java.lang.String getLanguage() {
    return this.language;
  }

  public ReceivePayload setLanguage(java.lang.String language) {
    this.language = language;
    return this;
  }

  public void unsetLanguage() {
    this.language = null;
  }

  /** Returns true if field language is set (has been assigned a value) and false otherwise */
  public boolean isSetLanguage() {
    return this.language != null;
  }

  public void setLanguageIsSet(boolean value) {
    if (!value) {
      this.language = null;
    }
  }

  public java.lang.String getContent() {
    return this.content;
  }

  public ReceivePayload setContent(java.lang.String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public int getSettingVarSize() {
    return (this.settingVar == null) ? 0 : this.settingVar.size();
  }

  public void putToSettingVar(java.lang.String key, java.lang.String val) {
    if (this.settingVar == null) {
      this.settingVar = new java.util.HashMap<java.lang.String,java.lang.String>();
    }
    this.settingVar.put(key, val);
  }

  public java.util.Map<java.lang.String,java.lang.String> getSettingVar() {
    return this.settingVar;
  }

  public ReceivePayload setSettingVar(java.util.Map<java.lang.String,java.lang.String> settingVar) {
    this.settingVar = settingVar;
    return this;
  }

  public void unsetSettingVar() {
    this.settingVar = null;
  }

  /** Returns true if field settingVar is set (has been assigned a value) and false otherwise */
  public boolean isSetSettingVar() {
    return this.settingVar != null;
  }

  public void setSettingVarIsSet(boolean value) {
    if (!value) {
      this.settingVar = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TRACE_ID:
      if (value == null) {
        unsetTraceId();
      } else {
        setTraceId((java.lang.String)value);
      }
      break;

    case LANGUAGE:
      if (value == null) {
        unsetLanguage();
      } else {
        setLanguage((java.lang.String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((java.lang.String)value);
      }
      break;

    case SETTING_VAR:
      if (value == null) {
        unsetSettingVar();
      } else {
        setSettingVar((java.util.Map<java.lang.String,java.lang.String>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TRACE_ID:
      return getTraceId();

    case LANGUAGE:
      return getLanguage();

    case CONTENT:
      return getContent();

    case SETTING_VAR:
      return getSettingVar();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TRACE_ID:
      return isSetTraceId();
    case LANGUAGE:
      return isSetLanguage();
    case CONTENT:
      return isSetContent();
    case SETTING_VAR:
      return isSetSettingVar();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ReceivePayload)
      return this.equals((ReceivePayload)that);
    return false;
  }

  public boolean equals(ReceivePayload that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_traceId = true && this.isSetTraceId();
    boolean that_present_traceId = true && that.isSetTraceId();
    if (this_present_traceId || that_present_traceId) {
      if (!(this_present_traceId && that_present_traceId))
        return false;
      if (!this.traceId.equals(that.traceId))
        return false;
    }

    boolean this_present_language = true && this.isSetLanguage();
    boolean that_present_language = true && that.isSetLanguage();
    if (this_present_language || that_present_language) {
      if (!(this_present_language && that_present_language))
        return false;
      if (!this.language.equals(that.language))
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_settingVar = true && this.isSetSettingVar();
    boolean that_present_settingVar = true && that.isSetSettingVar();
    if (this_present_settingVar || that_present_settingVar) {
      if (!(this_present_settingVar && that_present_settingVar))
        return false;
      if (!this.settingVar.equals(that.settingVar))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTraceId()) ? 131071 : 524287);
    if (isSetTraceId())
      hashCode = hashCode * 8191 + traceId.hashCode();

    hashCode = hashCode * 8191 + ((isSetLanguage()) ? 131071 : 524287);
    if (isSetLanguage())
      hashCode = hashCode * 8191 + language.hashCode();

    hashCode = hashCode * 8191 + ((isSetContent()) ? 131071 : 524287);
    if (isSetContent())
      hashCode = hashCode * 8191 + content.hashCode();

    hashCode = hashCode * 8191 + ((isSetSettingVar()) ? 131071 : 524287);
    if (isSetSettingVar())
      hashCode = hashCode * 8191 + settingVar.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ReceivePayload other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTraceId()).compareTo(other.isSetTraceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTraceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.traceId, other.traceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLanguage()).compareTo(other.isSetLanguage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLanguage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.language, other.language);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetContent()).compareTo(other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSettingVar()).compareTo(other.isSetSettingVar());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSettingVar()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.settingVar, other.settingVar);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ReceivePayload(");
    boolean first = true;

    sb.append("traceId:");
    if (this.traceId == null) {
      sb.append("null");
    } else {
      sb.append(this.traceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("language:");
    if (this.language == null) {
      sb.append("null");
    } else {
      sb.append(this.language);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("settingVar:");
    if (this.settingVar == null) {
      sb.append("null");
    } else {
      sb.append(this.settingVar);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ReceivePayloadStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ReceivePayloadStandardScheme getScheme() {
      return new ReceivePayloadStandardScheme();
    }
  }

  private static class ReceivePayloadStandardScheme extends org.apache.thrift.scheme.StandardScheme<ReceivePayload> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReceivePayload struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TRACE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.traceId = iprot.readString();
              struct.setTraceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LANGUAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.language = iprot.readString();
              struct.setLanguageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SETTING_VAR
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                struct.settingVar = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map0.size);
                java.lang.String _key1;
                java.lang.String _val2;
                for (int _i3 = 0; _i3 < _map0.size; ++_i3)
                {
                  _key1 = iprot.readString();
                  _val2 = iprot.readString();
                  struct.settingVar.put(_key1, _val2);
                }
                iprot.readMapEnd();
              }
              struct.setSettingVarIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReceivePayload struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.traceId != null) {
        oprot.writeFieldBegin(TRACE_ID_FIELD_DESC);
        oprot.writeString(struct.traceId);
        oprot.writeFieldEnd();
      }
      if (struct.language != null) {
        oprot.writeFieldBegin(LANGUAGE_FIELD_DESC);
        oprot.writeString(struct.language);
        oprot.writeFieldEnd();
      }
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      if (struct.settingVar != null) {
        oprot.writeFieldBegin(SETTING_VAR_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.settingVar.size()));
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter4 : struct.settingVar.entrySet())
          {
            oprot.writeString(_iter4.getKey());
            oprot.writeString(_iter4.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReceivePayloadTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ReceivePayloadTupleScheme getScheme() {
      return new ReceivePayloadTupleScheme();
    }
  }

  private static class ReceivePayloadTupleScheme extends org.apache.thrift.scheme.TupleScheme<ReceivePayload> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReceivePayload struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTraceId()) {
        optionals.set(0);
      }
      if (struct.isSetLanguage()) {
        optionals.set(1);
      }
      if (struct.isSetContent()) {
        optionals.set(2);
      }
      if (struct.isSetSettingVar()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTraceId()) {
        oprot.writeString(struct.traceId);
      }
      if (struct.isSetLanguage()) {
        oprot.writeString(struct.language);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetSettingVar()) {
        {
          oprot.writeI32(struct.settingVar.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter5 : struct.settingVar.entrySet())
          {
            oprot.writeString(_iter5.getKey());
            oprot.writeString(_iter5.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReceivePayload struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.traceId = iprot.readString();
        struct.setTraceIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.language = iprot.readString();
        struct.setLanguageIsSet(true);
      }
      if (incoming.get(2)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map6 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.settingVar = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map6.size);
          java.lang.String _key7;
          java.lang.String _val8;
          for (int _i9 = 0; _i9 < _map6.size; ++_i9)
          {
            _key7 = iprot.readString();
            _val8 = iprot.readString();
            struct.settingVar.put(_key7, _val8);
          }
        }
        struct.setSettingVarIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
