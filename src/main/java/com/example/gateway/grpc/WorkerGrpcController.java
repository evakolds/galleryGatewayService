package com.example.gateway.grpc;

import com.gallery.worker.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class WorkerGrpcController extends WorkerServiceGrpc.WorkerServiceImplBase {

    private String url = "gallery-workers";
    private final ManagedChannel channel = ManagedChannelBuilder.forAddress(url, 7085).usePlaintext().build();;
    private WorkerServiceGrpc.WorkerServiceBlockingStub stub = WorkerServiceGrpc.newBlockingStub(channel);

    @Override
    public void all(AllWorkersRequest request, StreamObserver<AllWorkersResponse> responseObserver) {
        AllWorkersRequest workerRequest = AllWorkersRequest.newBuilder().build();
        AllWorkersResponse workers = stub.all(workerRequest);
        responseObserver.onNext(workers);
        responseObserver.onCompleted();
    }

    @Override
    public void add(WorkerRequest request, StreamObserver<WorkerResponse> responseObserver) {
        WorkerRequest worker = WorkerRequest.newBuilder().
                setName(request.getName()).
                setSurname(request.getSurname()).
                setSalary(request.getSalary()).
                setPosition(request.getPosition()).
                build();
        WorkerResponse response = stub.add(worker);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void byId(WorkerByIdRequest request, StreamObserver<WorkerResponse> responseObserver) {
        WorkerByIdRequest workerRequest = WorkerByIdRequest.newBuilder().
                setId(request.getId()).
                build();
        WorkerResponse response = stub.byId(workerRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(WorkerByIdRequest request, StreamObserver<DeleteWorkerResponse> responseObserver) {
        WorkerByIdRequest workerRequest = WorkerByIdRequest.newBuilder().
                setId(request.getId()).
                build();
        DeleteWorkerResponse response = stub.delete(workerRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
