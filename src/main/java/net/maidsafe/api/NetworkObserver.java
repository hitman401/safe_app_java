package net.maidsafe.api;

import java.util.concurrent.CompletableFuture;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import net.maidsafe.api.model.App;
import net.maidsafe.binding.model.FfiCallback;
import net.maidsafe.binding.model.FfiResult;

public abstract class NetworkObserver {

	public enum Status {
		CONNECTED, CONNECTING, TERMINATED
	}

	private final NetworkObserver instance;
	private final PointerByReference appPointerRef = new PointerByReference();
	private final CompletableFuture<SafeClient> future = new CompletableFuture<SafeClient>();
	private App app;

	private final FfiCallback.NetworkObserverCallback observer = new FfiCallback.NetworkObserverCallback() {

		@Override
		public void onResponse(Pointer userData, FfiResult.ByVal result, int event) {
			if (result == null ||(!future.isDone() && !result.isError())) {
				app.setAppHandle(appPointerRef.getValue());
				future.complete(new SafeClient(app));
			}
			if (result != null && result.isError()) {
				instance.onError(result.error_code, result.description);
			} else {
				instance.onStateChange(Status.values()[event]);
			}
		}
	};

	public NetworkObserver() {
		instance = this;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public PointerByReference getAppRef() {
		return appPointerRef;
	}

	public FfiCallback.NetworkObserverCallback getObserver() {
		return observer;
	}

	public CompletableFuture<SafeClient> getFuture() {
		return future;
	}

	public abstract void onStateChange(Status event);

	public abstract void onError(int errorCode, String description);

}
