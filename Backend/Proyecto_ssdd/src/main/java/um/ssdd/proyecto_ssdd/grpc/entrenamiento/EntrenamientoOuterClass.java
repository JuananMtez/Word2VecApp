// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: entrenamiento.proto

package um.ssdd.proyecto_ssdd.grpc.entrenamiento;

public final class EntrenamientoOuterClass {
  private EntrenamientoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_entrenamiento_Entrenamiento_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_entrenamiento_Entrenamiento_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_entrenamiento_CrearEntrenamientoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_entrenamiento_CrearEntrenamientoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_entrenamiento_CrearEntrenamientoResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_entrenamiento_CrearEntrenamientoResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023entrenamiento.proto\022\rentrenamiento\032\033go" +
      "ogle/protobuf/empty.proto\"\033\n\rEntrenamien" +
      "to\022\n\n\002id\030\001 \001(\t\"P\n\031CrearEntrenamientoRequ" +
      "est\0223\n\rentrenamiento\030\001 \001(\0132\034.entrenamien" +
      "to.Entrenamiento\"Q\n\032CrearEntrenamientoRe" +
      "sponse\0223\n\rentrenamiento\030\001 \001(\0132\034.entrenam" +
      "iento.Entrenamiento2y\n\024EntrenamientoServ" +
      "ice\022a\n\010Entrenar\022(.entrenamiento.CrearEnt" +
      "renamientoRequest\032).entrenamiento.CrearE" +
      "ntrenamientoResponse\"\000B,\n(um.ssdd.proyec" +
      "to_ssdd.grpc.entrenamientoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_entrenamiento_Entrenamiento_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_entrenamiento_Entrenamiento_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_entrenamiento_Entrenamiento_descriptor,
        new java.lang.String[] { "Id", });
    internal_static_entrenamiento_CrearEntrenamientoRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_entrenamiento_CrearEntrenamientoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_entrenamiento_CrearEntrenamientoRequest_descriptor,
        new java.lang.String[] { "Entrenamiento", });
    internal_static_entrenamiento_CrearEntrenamientoResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_entrenamiento_CrearEntrenamientoResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_entrenamiento_CrearEntrenamientoResponse_descriptor,
        new java.lang.String[] { "Entrenamiento", });
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
