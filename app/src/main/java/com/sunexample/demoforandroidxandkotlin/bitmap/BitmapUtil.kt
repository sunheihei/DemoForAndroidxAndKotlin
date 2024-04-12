package com.sunexample.demoforandroidxandkotlin.bitmap

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Hashtable
import java.util.Locale

/**
 * Bitmap处理工具类
 *
 * @author 邹峰立
 */
class BitmapUtil {
    enum class BitmapFunCompressFormat {
        PNG, JPEG, WEBP
    }

    enum class BitmapFunConfig {
        RGB_565, ALPHA_8, ARGB_4444, ARGB_8888
    }

    /**
     * 将bitmap转换成File，写入SD卡
     *
     * @param bitmap   待处理位图
     * @param fileName 文件名称，带后缀
     * @return 文件URI
     */
    fun bitmapToFileUri(bitmap: Bitmap, fileName: String): Uri? {
        return saveBitmapToUri(bitmap, TEMP_PATH, fileName, BitmapFunCompressFormat.PNG)
    }

    companion object {
        private val TEMP_PATH = Environment.getExternalStorageDirectory()
            .toString() + File.separator + "temp" + File.separator

        /**
         * 本地图片转Bitmap，宽800,高800
         *
         * @param path 本地图片路径
         * @return 位图Bitmap
         */
        fun imgPathToReSizeBitmap(path: String): Bitmap? {
            return imgPathToReSizeBitmap(path, 800, 800)
        }

        /**
         * 根据图片路径压缩图片并转成Bitmap，宽和高同步递减
         *
         * @param imgPath 真实图片路径
         * @param width   图片最终宽
         * @param height  图片最终高
         */
        fun imgPathToReSizeBitmap(imgPath: String, width: Int, height: Int): Bitmap? {
            var bitmap: Bitmap? = null
            var `in`: FileInputStream? = null
            try {
                val imgFile = File(imgPath)
                if (imgFile.exists() && imgFile.isFile) {
                    `in` = FileInputStream(imgFile)
                    val options = BitmapFactory.Options()
                    // 生产Bitmap不分配内存空间
                    options.inJustDecodeBounds = true
                    // 传递图片，主要为了获取图片宽和高
                    BitmapFactory.decodeStream(`in`, null, options)
                    `in`.close()
                    var i = 0
                    while (true) {
                        if (options.outWidth shr i <= width && options.outHeight shr i <= height) {
                            `in` = FileInputStream(File(imgPath))
                            // 新生成的图是原图的几分之几
                            options.inSampleSize = Math.pow(2.0, i.toDouble()).toInt()
                            // 生产Bitmap分配内存空间
                            options.inJustDecodeBounds = false
                            bitmap = BitmapFactory.decodeStream(`in`, null, options)
                            break
                        }
                        i += 1
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (`in` != null) {
                    try {
                        `in`.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return bitmap
        }

        /**
         * 按照质量来压缩图片，压缩到<=size
         *
         * @param image   待压缩位图
         * @param maxSize 最大大小（K）
         * @return 位图Bitmap
         */
        fun compressBitmapByQuality(image: Bitmap, maxSize: Int): Bitmap? {
            return compressBitmapByQuality(image, maxSize, BitmapFunCompressFormat.PNG)
        }

        /**
         * Bitmap按照质量来压缩图片，压缩到<=maxSize
         *
         * @param bitmap                  bitmap数据源
         * @param maxSize                 最大大小（K）
         * @param bitmapFunCompressFormat 图片格式
         */
        fun compressBitmapByQuality(
            bitmap: Bitmap, maxSize: Int,
            bitmapFunCompressFormat: BitmapFunCompressFormat?
        ): Bitmap? {
            var bCompressFormat = CompressFormat.PNG
            if (bitmapFunCompressFormat != null) {
                if (bitmapFunCompressFormat == BitmapFunCompressFormat.JPEG) bCompressFormat =
                    CompressFormat.JPEG else if (bitmapFunCompressFormat == BitmapFunCompressFormat.WEBP) bCompressFormat =
                    CompressFormat.WEBP
            }
            val baos = ByteArrayOutputStream()
            bitmap.compress(bCompressFormat, 100, baos) // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            var options = 100
            while (baos.toByteArray().size / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于maxSize kb,大于继续压缩
                if (options > 0) {
                    baos.reset() // 重置baos，即清空baos
                    options -= 20 // 每次都减少20
                    bitmap.compress(bCompressFormat, options, baos) // 这里压缩options%，把压缩后的数据存放到baos中
                } else {
                    break
                }
            }
            val isBm =
                ByteArrayInputStream(baos.toByteArray()) // 把压缩后的数据baos存放到ByteArrayInputStream中
            val resultBitmap =
                BitmapFactory.decodeStream(isBm, null, null) // 把ByteArrayInputStream数据生成图片
            try {
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                isBm.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return resultBitmap
        }

        /**
         * 图片按比例大小压缩方法
         *
         * @param image  图片资源
         * @param pixelW 宽
         * @param pixelH 高
         * @return 位图Bitmap
         */
        fun compressBitmapByRatio(image: Bitmap, pixelW: Float, pixelH: Float): Bitmap? {
            return compressBitmapByRatio(
                image,
                pixelW,
                pixelH,
                BitmapFunConfig.RGB_565,
                BitmapFunCompressFormat.PNG
            )
        }

        /**
         * Bitmap图片按比例大小压缩方法
         *
         * @param bitmap                  Bitmap数据源
         * @param pixelW                  对比宽
         * @param pixelH                  对比高
         * @param bitmapFunConfig         BitmapConfig
         * @param bitmapFunCompressFormat 图片格式
         */
        fun compressBitmapByRatio(
            bitmap: Bitmap, pixelW: Float, pixelH: Float,
            bitmapFunConfig: BitmapFunConfig?,
            bitmapFunCompressFormat: BitmapFunCompressFormat?
        ): Bitmap? {
            var bConfig = Bitmap.Config.RGB_565
            if (bitmapFunConfig != null) {
                if (bitmapFunConfig == BitmapFunConfig.ARGB_8888) bConfig =
                    Bitmap.Config.ARGB_8888 else if (bitmapFunConfig == BitmapFunConfig.ALPHA_8) bConfig =
                    Bitmap.Config.ALPHA_8 else if (bitmapFunConfig == BitmapFunConfig.ARGB_4444) bConfig =
                    Bitmap.Config.ARGB_4444
            }
            var bCompressFormat = CompressFormat.PNG
            if (bitmapFunCompressFormat != null) {
                if (bitmapFunCompressFormat == BitmapFunCompressFormat.JPEG) bCompressFormat =
                    CompressFormat.JPEG else if (bitmapFunCompressFormat == BitmapFunCompressFormat.WEBP) bCompressFormat =
                    CompressFormat.WEBP
            }
            val os = ByteArrayOutputStream()
            bitmap.compress(bCompressFormat, 100, os)
            if (os.toByteArray().size / 1024 > 2048) { // 判断如果图片大于2M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                os.reset() // 重置baos即清空baos
                bitmap.compress(bCompressFormat, 50, os) // 这里压缩50%，把压缩后的数据存放到baos中
            }
            var `is` = ByteArrayInputStream(os.toByteArray())
            val newOpts = BitmapFactory.Options()
            // 开始读入图片，此时把options.inJustDecodeBounds 设true
            newOpts.inJustDecodeBounds = true
            newOpts.inPreferredConfig = bConfig
            val resultBitmap: Bitmap?
            BitmapFactory.decodeStream(`is`, null, newOpts)
            newOpts.inJustDecodeBounds = false
            val w = newOpts.outWidth
            val h = newOpts.outHeight
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            var be = 1 //be=1表示不缩放
            if (w > h && w > pixelW) { // 如果宽度大的话根据宽度固定大小缩放
                be = (newOpts.outWidth / pixelW).toInt()
            } else if (w < h && h > pixelH) { // 如果高度高的话根据宽度固定大小缩放
                be = (newOpts.outHeight / pixelH).toInt()
            }
            if (be <= 0) be = 1
            newOpts.inSampleSize = be // 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设false
            `is` = ByteArrayInputStream(os.toByteArray())
            resultBitmap = BitmapFactory.decodeStream(`is`, null, newOpts)
            try {
                os.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return resultBitmap
        }
        /**
         * 通过uri获取图片并进行比例压缩和进行一次质量压缩，大于1M的时候进行质量压缩
         *
         * @param activity                上下文对象
         * @param uri                     uri数据源
         * @param bitmapFunConfig         BitmapConfig
         * @param bitmapFunCompressFormat 图片格式
         */
        /**
         * 通过uri获取图片并进行比例压缩
         *
         * @param activity 当前Activity
         * @param uri      图片Uri信息
         * @return 位图Bitmap
         */
        @JvmOverloads
        fun uriToReSizeBitmap(
            activity: Activity, uri: Uri,
            bitmapFunConfig: BitmapFunConfig? = BitmapFunConfig.ARGB_8888,
            bitmapFunCompressFormat: BitmapFunCompressFormat? = BitmapFunCompressFormat.PNG
        ): Bitmap? {
            var bitmap: Bitmap? = null
            var input: InputStream? = null
            try {
                var bConfig = Bitmap.Config.ARGB_8888
                if (bitmapFunConfig != null) {
                    if (bitmapFunConfig == BitmapFunConfig.RGB_565) bConfig =
                        Bitmap.Config.RGB_565 else if (bitmapFunConfig == BitmapFunConfig.ALPHA_8) bConfig =
                        Bitmap.Config.ALPHA_8 else if (bitmapFunConfig == BitmapFunConfig.ARGB_4444) bConfig =
                        Bitmap.Config.ARGB_4444
                }
                var bCompressFormat = CompressFormat.PNG
                if (bitmapFunCompressFormat != null) {
                    if (bitmapFunCompressFormat == BitmapFunCompressFormat.JPEG) bCompressFormat =
                        CompressFormat.JPEG else if (bitmapFunCompressFormat == BitmapFunCompressFormat.WEBP) bCompressFormat =
                        CompressFormat.WEBP
                }
                input = activity.contentResolver.openInputStream(uri)
                val onlyBoundsOptions = BitmapFactory.Options()
                onlyBoundsOptions.inJustDecodeBounds = true
                onlyBoundsOptions.inDither = true
                onlyBoundsOptions.inPreferredConfig = bConfig
                BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
                input?.close()
                val originalWidth = onlyBoundsOptions.outWidth
                val originalHeight = onlyBoundsOptions.outHeight
                if (originalWidth == -1 || originalHeight == -1) return null

                //图片分辨率以480x800为标准
                val hh = 800f //这里设置高度为800f
                val ww = 480f //这里设置宽度为480f
                //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                var be = 1 //be=1表示不缩放
                if (originalWidth > originalHeight && originalWidth > ww) { //如果宽度大的话根据宽度固定大小缩放
                    be = (originalWidth / ww).toInt()
                } else if (originalWidth < originalHeight && originalHeight > hh) { //如果高度高的话根据宽度固定大小缩放
                    be = (originalHeight / hh).toInt()
                }
                if (be <= 0) be = 1

                //比例压缩
                val bitmapOptions = BitmapFactory.Options()
                bitmapOptions.inSampleSize = be //设置缩放比例
                bitmapOptions.inDither = true
                bitmapOptions.inPreferredConfig = bConfig
                input = activity.contentResolver.openInputStream(uri)
                bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
                input?.close()

                //做一次质量压缩
                val baos = ByteArrayOutputStream()
                if (bitmap != null) {
                    bitmap.compress(bCompressFormat, 100, baos)
                    if (baos.toByteArray().size / 1024 > 1024) { // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                        baos.reset() // 重置baos即清空baos
                        bitmap.compress(bCompressFormat, 50, baos) // 这里压缩50%，把压缩后的数据存放到baos中
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return bitmap
        }

        /**
         * 本地图片转化为Bitmap
         *
         * @param path 本地图片路径
         * @return 位图Bitmap
         */
        fun imgPathToBitmap(path: String): Bitmap? {
            var bitmap: Bitmap? = null
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(path)
                bitmap = BitmapFactory.decodeStream(fis) // 把流转化为Bitmap图片
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return bitmap
        }

        /**
         * 由图片路径转换成Bitmap
         *
         * @param imgPath         图片路径
         * @param bitmapFunConfig BitmapConfig
         */
        fun imgPathToBitmap(
            imgPath: String,
            bitmapFunConfig: BitmapFunConfig?
        ): Bitmap {
            val options = BitmapFactory.Options()
            // 分配内存空间
            options.inJustDecodeBounds = false
            options.inPurgeable = true
            options.inInputShareable = true
            // 不进行压缩
            options.inSampleSize = 1
            options.inPreferredConfig = Bitmap.Config.RGB_565
            if (bitmapFunConfig != null) {
                if (bitmapFunConfig == BitmapFunConfig.ALPHA_8) options.inPreferredConfig =
                    Bitmap.Config.ALPHA_8 else if (bitmapFunConfig == BitmapFunConfig.ARGB_4444) options.inPreferredConfig =
                    Bitmap.Config.ARGB_4444 else if (bitmapFunConfig == BitmapFunConfig.ARGB_8888) options.inPreferredConfig =
                    Bitmap.Config.ARGB_8888
            }
            return BitmapFactory.decodeFile(imgPath, options)
        }

        /**
         * 将Bitmap转换成File，写入SD卡
         *
         * @param bitmap                  Bitmap数据源
         * @param filePath                保存SD卡文件路径
         * @param fileName                文件名称，带后缀
         * @param bitmapFunCompressFormat 图片格式
         * @return Uri
         */
        fun saveBitmapToUri(
            bitmap: Bitmap, filePath: String, fileName: String,
            bitmapFunCompressFormat: BitmapFunCompressFormat?
        ): Uri? {
            var fileName = fileName
            var uri: Uri? = null
            var fos: FileOutputStream? = null
            // 创建文件夹
            val tmpDir = File(filePath)
            var isMkdirsSuccess = tmpDir.exists()
            if (!isMkdirsSuccess) isMkdirsSuccess = tmpDir.mkdirs()
            if (isMkdirsSuccess) {
                // 创建文件
                if (TextUtils.isEmpty(fileName)) fileName =
                    System.currentTimeMillis().toString() + ".png"
                val imgFile = File(tmpDir.absolutePath + fileName)
                try {
                    // bitmap写入文件
                    fos = FileOutputStream(imgFile)
                    bitmap.compress(CompressFormat.PNG, 100, fos)
                    if (bitmapFunCompressFormat != null) {
                        if (bitmapFunCompressFormat == BitmapFunCompressFormat.JPEG) bitmap.compress(
                            CompressFormat.JPEG, 100, fos
                        ) else if (bitmapFunCompressFormat == BitmapFunCompressFormat.WEBP) bitmap.compress(
                            CompressFormat.WEBP, 100, fos
                        )
                    }
                    fos.flush()
                    fos.close()
                    uri = Uri.fromFile(imgFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (fos != null) {
                        try {
                            fos.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return uri
        }
        /**
         * 将Bitmap转换成图片文件，写入SD卡
         *
         * @param bitmap                  Bitmap数据源
         * @param filePath                保存SD卡文件路径
         * @param bitmapFunCompressFormat 图片格式
         * @return ImagePath
         */
        /**
         * 将bitmap转换成图片文件，写入SD卡
         *
         * @param bitmap 待转换位图
         * @return 文件路径
         */
        @JvmOverloads
        fun saveBitmapToImgPath(
            bitmap: Bitmap, filePath: String = TEMP_PATH,
            bitmapFunCompressFormat: BitmapFunCompressFormat? = BitmapFunCompressFormat.PNG
        ): String? {
            var fos: FileOutputStream? = null
            var imgPath: String? = null
            // 创建文件夹
            val tmpDir = File(filePath)
            var isMkdirsSuccess = tmpDir.exists()
            if (!isMkdirsSuccess) isMkdirsSuccess = tmpDir.mkdirs()
            if (isMkdirsSuccess) {
                // 保存文件路径
                imgPath = tmpDir.absolutePath + System.currentTimeMillis() + ".png"
                // 创建文件
                val imgFile = File(imgPath)
                try {
                    // bitmap写入文件
                    fos = FileOutputStream(imgFile)
                    bitmap.compress(CompressFormat.PNG, 100, fos)
                    if (bitmapFunCompressFormat != null) {
                        if (bitmapFunCompressFormat == BitmapFunCompressFormat.JPEG) bitmap.compress(
                            CompressFormat.JPEG, 100, fos
                        ) else if (bitmapFunCompressFormat == BitmapFunCompressFormat.WEBP) bitmap.compress(
                            CompressFormat.WEBP, 100, fos
                        )
                    }
                    fos.flush()
                    fos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (fos != null) {
                        try {
                            fos.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return imgPath
        }

        /**
         * 转化成带删除按钮的图片
         *
         * @param context        上下文对象
         * @param x              图像的宽度
         * @param y              图像的高度
         * @param image          源图片
         * @param outerRadiusRat 圆角的大小
         * @return 圆角图片
         */
        fun bitmapWithDelImg(
            context: Context,
            x: Int,
            y: Int,
            image: Bitmap,
            outerRadiusRat: Float
        ): Bitmap {
            // 根据源文件新建一个darwable对象
            val imageDrawable: Drawable = BitmapDrawable(context.resources, image)

            // 新建一个新的输出图片
            val output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)

            // 新建一个矩形
            val outerRect = RectF(0f, 0f, x.toFloat(), y.toFloat())

            // 产生一个红色的圆角矩形
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.RED
            canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint)

            // 将源图片绘制到这个圆角矩形上
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            imageDrawable.setBounds(0, 0, x, y)
            canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG)
            imageDrawable.draw(canvas)
            canvas.restore()
            return output
        }

        /**
         * 获取视频帧 - 子线程
         *
         * @param path 视频地址
         */
        fun getVideoThumb(path: String): Bitmap? {
            return getVideoThumb(path, 0)
        }

        /**
         * 获取视频帧 - 子线程
         *
         * @param path 视频地址
         * @param time 时间
         */
        fun getVideoThumb(path: String, time: Long): Bitmap? {
            return getVideoThumb(path, time, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        }

        /**
         * 获取视频帧 - 子线程
         *
         * @param path   视频地址
         * @param time   时间
         * @param option 获取帧类型
         */
        fun getVideoThumb(path: String, time: Long, option: Int): Bitmap? {
            var bitmap: Bitmap? = null
            val retriever = MediaMetadataRetriever()
            try {
                val formatPath = path.lowercase(Locale.getDefault())
                if (formatPath.startsWith("http://")
                    || formatPath.startsWith("https://")
                    || formatPath.startsWith("widevine://")
                ) {
                    // 网络
                    retriever.setDataSource(path, Hashtable())
                } else {
                    // 本地
                    retriever.setDataSource(path)
                }
                // OPTION_CLOSEST 精确
//            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
                bitmap = if (time < 0) {
                    // 获取时长，单位：毫秒
                    val duration =
                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                    // 取视频时长中点的最近一个关键帧
                    val frameTime = duration!!.toLong() * 1000 / 2
                    // 参数单位为微秒，1毫秒 = 1000微秒
                    retriever.getFrameAtTime(frameTime, option)
                } else if (time == 0L) {
                    if (option == MediaMetadataRetriever.OPTION_CLOSEST_SYNC) retriever.frameAtTime else retriever.getFrameAtTime(
                        0,
                        option
                    )
                } else {
                    retriever.getFrameAtTime(time, option)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                try {
                    retriever.release()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            return bitmap
        }

        /**
         * 获取音频封面
         *
         * @param path MP3文件路径
         */
        fun getAudioThumb(path: String): ByteArray? {
            var picture: ByteArray? = null
            try {
                val retriever = MediaMetadataRetriever()
                val formatPath = path.lowercase(Locale.getDefault())
                if (formatPath.startsWith("http://")
                    || formatPath.startsWith("https://")
                    || formatPath.startsWith("widevine://")
                ) {
                    // 网络
                    retriever.setDataSource(path, Hashtable())
                } else {
                    // 本地
                    retriever.setDataSource(path)
                }
                picture = retriever.embeddedPicture
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return picture
        }

        /**
         * Bitmap转File
         *
         * @param bitmap 待转换数据
         */
        fun bitmapToFile(bitmap: Bitmap): File {
            val baos = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.JPEG, 100, baos)
            val tempFile = File(TEMP_PATH + System.currentTimeMillis() + ".jpg")
            var fos: FileOutputStream? = null
            var `is`: InputStream? = null
            try {
                fos = FileOutputStream(tempFile)
                `is` = ByteArrayInputStream(baos.toByteArray())
                var x: Int
                val b = ByteArray(1024 * 100)
                while (`is`.read(b).also { x = it } != -1) {
                    fos.write(b, 0, x)
                }
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    baos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                if (`is` != null) {
                    try {
                        `is`.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return tempFile
        }
    }
}