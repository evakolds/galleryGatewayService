package com.gallery.gateway.grpc;

import com.gallery.exhibition.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class ExhibitionGrpcController extends ExhibitionServiceGrpc.ExhibitionServiceImplBase {

    private String url = "gallery-exhibitions";
    private final ManagedChannel channel = ManagedChannelBuilder.forAddress(url, 7084).usePlaintext().build();;
    private ExhibitionServiceGrpc.ExhibitionServiceBlockingStub stub = ExhibitionServiceGrpc.newBlockingStub(channel);

    @Override
    public void all(AllExhibitionsRequest request, StreamObserver<AllExhibitionsResponse> responseObserver) {
        AllExhibitionsRequest exhibitionRequest = AllExhibitionsRequest.newBuilder().build();
        AllExhibitionsResponse response = stub.all(exhibitionRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(ExhibitionRequest request, StreamObserver<ExhibitionResponse> responseObserver) {
        ExhibitionRequest exhibition = ExhibitionRequest.newBuilder().
                setName(request.getName()).
                setOpen(request.getOpen()).
                setPrice(request.getPrice()).
                build();
        ExhibitionResponse response = stub.add(exhibition);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void byId(ExhibitionByIdRequest request, StreamObserver<ExhibitionResponse> responseObserver) {
        ExhibitionByIdRequest exhibitionRequest = ExhibitionByIdRequest.newBuilder().
                setId(request.getId()).
                build();
        ExhibitionResponse response = stub.byId(exhibitionRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(ExhibitionByIdRequest request, StreamObserver<DeleteExhibitionResponse> responseObserver) {
        ExhibitionByIdRequest exhibitionRequest = ExhibitionByIdRequest.newBuilder().
                setId(request.getId()).
                build();
        DeleteExhibitionResponse response = stub.delete(exhibitionRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
