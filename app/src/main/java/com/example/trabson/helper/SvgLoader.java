package com.example.trabson.helper;

import android.content.Context;
import android.widget.ImageView;

import coil.ComponentRegistry;
import coil.ImageLoader;
import coil.decode.SvgDecoder;
import coil.request.ImageRequest;

public class SvgLoader {

    public static void loadSvg(Context context, String url, ImageView imageView) {
        ImageLoader imageLoader = createImageLoader(context);

        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    public static ImageLoader createImageLoader(Context context) {
        return new ImageLoader.Builder(context)
                .components(new ComponentRegistry.Builder()
                        .add(new SvgDecoder.Factory())
                        .build()
                )
                .build();
    }
}
