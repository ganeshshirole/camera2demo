package com.example.android.camera2.video.utils

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CaptureRequest
import android.util.Size
import android.view.Surface
import com.example.android.camera.utils.AutoFitSurfaceView

abstract class Pipeline(width: Int, height: Int, fps: Int, filterOn: Boolean,
                        dynamicRange: Long, characteristics: CameraCharacteristics, encoder: EncoderWrapper,
                        viewFinder: AutoFitSurfaceView) {
    protected val width = width
    protected val height = height
    protected val fps = fps
    protected val filterOn = filterOn
    protected val dynamicRange = dynamicRange
    protected val characteristics = characteristics
    protected val encoder = encoder
    protected val viewFinder = viewFinder

    open public fun createPreviewRequest(session: CameraCaptureSession,
            previewStabilization: Boolean): CaptureRequest? {
        return null
    }

    public abstract fun createRecordRequest(session: CameraCaptureSession,
            previewStabilization: Boolean): CaptureRequest

    open public fun destroyWindowSurface() { }

    open public fun setPreviewSize(previewSize: Size) { }

    open public fun createResources(surface: Surface) { }

    public abstract fun getPreviewTargets(): List<Surface>

    public abstract fun getRecordTargets(): List<Surface>

    open public fun actionDown(encoderSurface: Surface) { }

    open public fun clearFrameListener() { }

    open public fun cleanup() { }

    open public fun startRecording() { }

    open public fun stopRecording() { }
}