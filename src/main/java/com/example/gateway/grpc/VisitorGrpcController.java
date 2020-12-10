package com.gallery.gateway.grpc;

import com.gallery.visitor.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class VisitorGrpcController extends VisitorServiceGrpc.VisitorServiceImplBase {

    private String url = "gallery-visitors";
    private final ManagedChannel channel = ManagedChannelBuilder.forAddress(url, 7084).usePlaintext().build();;
    private VisitorServiceGrpc.VisitorServiceBlockingStub stub = VisitorServiceGrpc.newBlockingStub(channel);

    @Override
    public void all(AllVisitorsRequest request, StreamObserver<AllVisitorsResponse> responseObserver) {
        AllVisitorsRequest visitorRequest = AllVisitorsRequest.newBuilder().build();
        AllVisitorsResponse response = stub.all(visitorRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(VisitorRequest request, StreamObserver<VisitorResponse> responseObserver) {
        VisitorRequest visitor = VisitorRequest.newBuilder().
                setName(request.getName()).
                setMoney(request.getMoney()).
                setAge(request.getAge()).
                build();
        VisitorResponse response = stub.add(visitor);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void byId(VisitorByIdRequest request, StreamObserver<VisitorResponse> responseObserver) {
        VisitorByIdRequest visitorRequest = VisitorByIdRequest.newBuilder().
                setId(request.getId()).
                build();
        VisitorResponse response = stub.byId(visitorRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(VisitorByIdRequest request, StreamObserver<DeleteVisitorResponse> responseObserver) {
        VisitorByIdRequest visitorRequest = VisitorByIdRequest.newBuilder().
                setId(request.getId()).
                build();
        DeleteVisitorResponse response = stub.delete(visitorRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
