package com.zhutingyu.learn.uidemo

import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import java.lang.StringBuilder

/**
 * @author Quiet Zhu
 * @date 2020-04-02
 * @description 可折叠文本
 */
class ExpandTextView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    private val textMore = "... 查看全部"
    private var viewW = 0
    private var maxLine = 3
    private var sourceString = ""
    private var tempLineStrings = mutableListOf<String>()
    
    private var tempLineCount = 1
    
    fun setTextString(text: String) {
        tempLineCount = 1
        sourceString = text
        post {
            viewW = measuredWidth
            
            val st = StaticLayout(
                sourceString,
                paint,
                viewW,
                Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier,
                0f,
                false
            )
            Log.e("Log", "line Count${st.lineCount}")
            
            if (st.lineCount > 3) {
                cutString(sourceString)
                setOnClickListener {
                    super.setText(sourceString)
                }
            } else {
                super.setText(sourceString)
            }
            
        }
    }
    
    
    private fun cutString(sourceString: String) {
        val list = sourceString.split("\n")
        val sb = StringBuilder()
        for (i in list.indices) {
            val isGoOn = autoStrings(sb, list[i])
            if (!isGoOn) {
                break
            }
            if (i != list.size - 1) {
                newLine(sb)
            }
        }
        //把结尾多余的\n去掉
        if (sb.endsWith("\n")) {
            sb.deleteCharAt(sb.length - 1)
        }
        val spn = SpannableStringBuilder(sb.toString())
        val newList = spn.toString().split("\n")
        autoLineString(spn, newList[newList.size - 1])
        super.setText(spn, BufferType.SPANNABLE)
    }
    
    private fun autoStrings(sb: StringBuilder, str: String): Boolean {
        //如果整行宽度在控件可用宽度之内，就不处理了
        if (paint.measureText(str) <= viewW) {
            if (tempLineCount > maxLine) {
                return false
            }
            sb.append(str)
        } else {
            //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
            var lineWidth = 0f
            var cnt = 0
            while (cnt != str.length) {
                if (tempLineCount > maxLine) {
                    return false
                }
                val ch = str[cnt]
                lineWidth += getStringW(ch.toString())
                if (lineWidth <= viewW) {
                    sb.append(ch)
                } else {
                    newLine(sb)
                    lineWidth = 0f
                    --cnt
                }
                ++cnt
            }
        }
        return true
    }
    
    //操作最后一行 让宽度刚好能放下查看更多
    private fun autoLineString(sb: SpannableStringBuilder, str: String) {
        if (paint.measureText(str) <= viewW - getStringW(textMore)) {
            val empty = "   "
            var lineW = getStringW(str)
            while (lineW < viewW - getStringW(textMore)) {
                lineW += getStringW(empty)
                if (lineW <= viewW - getStringW(textMore)) {
                    sb.append(empty)
                }
            }
        } else {
            var tempS = str
            while (getStringW(tempS) > viewW - getStringW(textMore)) {
                tempS = tempS.substring(0, tempS.length - 1)
                sb.delete(sb.length - 1, sb.length)
            }
        }
        sb.append(getMoreSpan())
    }
    
    private fun getStringW(s: String): Float {
        return paint.measureText(s)
    }
    
    private fun newLine(sb: StringBuilder) {
        sb.append("\n")
        tempLineCount++
    }
    
    private fun getMoreSpan(): SpannableString {
        val sbMore = SpannableString(textMore)
        sbMore.setSpan(
            ForegroundColorSpan(Color.BLUE),
            0,
            textMore.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return sbMore
    }
}