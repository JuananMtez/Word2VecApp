package um.ssdd.proyecto_ssdd.grpc.entrenamiento;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: entrenamiento.proto")
public final class EntrenamientoServiceGrpc {

  private EntrenamientoServiceGrpc() {}

  public static final String SERVICE_NAME = "entrenamiento.EntrenamientoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest,
      um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse> getEntrenarMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Entrenar",
      requestType = um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest.class,
      responseType = um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest,
      um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse> getEntrenarMethod() {
    io.grpc.MethodDescriptor<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest, um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse> getEntrenarMethod;
    if ((getEntrenarMethod = EntrenamientoServiceGrpc.getEntrenarMethod) == null) {
      synchronized (EntrenamientoServiceGrpc.class) {
        if ((getEntrenarMethod = EntrenamientoServiceGrpc.getEntrenarMethod) == null) {
          EntrenamientoServiceGrpc.getEntrenarMethod = getEntrenarMethod =
              io.grpc.MethodDescriptor.<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest, um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Entrenar"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EntrenamientoServiceMethodDescriptorSupplier("Entrenar"))
              .build();
        }
      }
    }
    return getEntrenarMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EntrenamientoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EntrenamientoServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EntrenamientoServiceStub>() {
        @java.lang.Override
        public EntrenamientoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EntrenamientoServiceStub(channel, callOptions);
        }
      };
    return EntrenamientoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EntrenamientoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EntrenamientoServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EntrenamientoServiceBlockingStub>() {
        @java.lang.Override
        public EntrenamientoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EntrenamientoServiceBlockingStub(channel, callOptions);
        }
      };
    return EntrenamientoServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EntrenamientoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EntrenamientoServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EntrenamientoServiceFutureStub>() {
        @java.lang.Override
        public EntrenamientoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EntrenamientoServiceFutureStub(channel, callOptions);
        }
      };
    return EntrenamientoServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class EntrenamientoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void entrenar(um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest request,
        io.grpc.stub.StreamObserver<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEntrenarMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getEntrenarMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest,
                um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse>(
                  this, METHODID_ENTRENAR)))
          .build();
    }
  }

  /**
   */
  public static final class EntrenamientoServiceStub extends io.grpc.stub.AbstractAsyncStub<EntrenamientoServiceStub> {
    private EntrenamientoServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EntrenamientoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EntrenamientoServiceStub(channel, callOptions);
    }

    /**
     */
    public void entrenar(um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest request,
        io.grpc.stub.StreamObserver<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEntrenarMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EntrenamientoServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<EntrenamientoServiceBlockingStub> {
    private EntrenamientoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EntrenamientoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EntrenamientoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse entrenar(um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEntrenarMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EntrenamientoServiceFutureStub extends io.grpc.stub.AbstractFutureStub<EntrenamientoServiceFutureStub> {
    private EntrenamientoServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EntrenamientoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EntrenamientoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse> entrenar(
        um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEntrenarMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ENTRENAR = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EntrenamientoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EntrenamientoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ENTRENAR:
          serviceImpl.entrenar((um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest) request,
              (io.grpc.stub.StreamObserver<um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EntrenamientoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EntrenamientoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return um.ssdd.proyecto_ssdd.grpc.entrenamiento.EntrenamientoOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EntrenamientoService");
    }
  }

  private static final class EntrenamientoServiceFileDescriptorSupplier
      extends EntrenamientoServiceBaseDescriptorSupplier {
    EntrenamientoServiceFileDescriptorSupplier() {}
  }

  private static final class EntrenamientoServiceMethodDescriptorSupplier
      extends EntrenamientoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EntrenamientoServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EntrenamientoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EntrenamientoServiceFileDescriptorSupplier())
              .addMethod(getEntrenarMethod())
              .build();
        }
      }
    }
    return result;
  }
}
