package com.android.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.core.R;
import com.android.core.utils.Text.StringUtils;


/**
 * @author CentMeng csdn@vip.163.com on 15/10/19.
 *         右边提示错误，下划线变色的EditText
 */
public class TipEditText extends RelativeLayout {

    private TextView textView;

    private EditText editText;

    private View line;

    private static final int LINE_VISIBLE = 0;

    private static final int LINE_INVISIBILE = 1;

    private final int default_text_color = Color.rgb(255, 0, 0);

    private final int defalut_line_color = Color.rgb(143, 143, 143);

    /**
     * 错误格式线
     */
    private final int default_line_tip_color = Color.rgb(255, 0, 0);

    /**
     * 提示信息
     */
    private String tipText = "格式错误";

    private String nullTipText = "请输入";

    private String hintText;

    private int textColor;

    private int lineColor;

    private int lineTipColor;

    private boolean singleline;

    private int inputType;

    private int maxlength;

    private boolean canNull = false;

    public final static int INPUTTYPE_MOBILE = 1;

    public final static int INPUTTYPE_OTHER = 2;

    private int watcher;

    public final static int WATCHER_MOBILE = 1;

    public final static int WATCEHER_NAME = 2;

    public final static int WATCHER_NULL = 3;

    private int lineVisible = LINE_VISIBLE;

    private boolean right = true;


    public TipEditText(Context context) {
        super(context);
    }

    public TipEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.edit_tip, this, true);
        if (textView == null || editText == null) {
            textView = (TextView) findViewById(R.id.textview);
            editText = (EditText) findViewById(R.id.edittext);
            line = (View) findViewById(R.id.line);
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TipEditText);
            textColor = attributes.getColor(R.styleable.TipEditText_tipedit_tipcolor, default_text_color);
            lineColor = attributes.getColor(R.styleable.TipEditText_tipedit_linecolor, defalut_line_color);
            lineTipColor = attributes.getColor(R.styleable.TipEditText_tipedit_linetipcolor, default_line_tip_color);
            hintText = attributes.getString(R.styleable.TipEditText_tipedit_hint);
            tipText = attributes.getString(R.styleable.TipEditText_tipedit_text);
            if (TextUtils.isEmpty(tipText)) {
                tipText = "格式错误";
            }
            lineVisible = attributes.getInt(R.styleable.TipEditText_tipedit_line_visibility, LINE_VISIBLE);
            if (lineVisible != LINE_VISIBLE) {
                line.setVisibility(View.INVISIBLE);
            }
            canNull = attributes.getBoolean(R.styleable.TipEditText_tipedit_cannull, false);

            /**
             * 输入最大字数
             */
            maxlength = attributes.getInt(R.styleable.TipEditText_tipedit_maxlength, Integer.MAX_VALUE);


            /**
             * 输入类型
             */
            inputType = attributes.getInt(R.styleable.TipEditText_tipedit_inputtype, INPUTTYPE_OTHER);


            /**
             * 是否单行
             */
            singleline = attributes.getBoolean(R.styleable.TipEditText_tipedit_singleline, false);


            /**
             * watcher
             */
            watcher = attributes.getInt(R.styleable.TipEditText_tipedit_watcher, 0);
            init();
        }

    }


    public TipEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init() {

        if (inputType == INPUTTYPE_MOBILE) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        }
        switch (watcher) {
            case WATCHER_MOBILE:
                editText.addTextChangedListener(mobileWatcher);
                break;
            case WATCEHER_NAME:
                editText.addTextChangedListener(nameWatcher);
                break;
            case WATCHER_NULL:
                editText.addTextChangedListener(normalWatcher);
                break;
        }
        editText.setSingleLine(singleline);
        editText.setHint(hintText);
        textView.setTextColor(textColor);
        line.setBackgroundColor(lineColor);
        setTrue();
    }

    private TextWatcher normalWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                byte[] bytes = s.toString().trim().getBytes("utf-8");
                if (bytes.length > maxlength * 3) {
                    int pos = s.length() - 1;
                    s.delete(pos, pos + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s.toString().trim().length() == 0) {
                if (!canNull) {
                    setFalse(true);
                } else {
                    setTrue();
                }
            } else {
                setTrue();
            }
        }
    };


    private TextWatcher mobileWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count == 11) {
                if (StringUtils.isMobileNO(s.toString(), "")) {
                    setTrue();
                } else {
                    setFalse(false);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 0) {
                if (!canNull) {
                    setFalse(true);
                } else {
                    setTrue();
                }
            } else {
                if (StringUtils.isNumber(s.toString().trim())) {
                    setTrue();
                } else {
                    setFalse(false);
                }
            }
        }
    };

    private TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                byte[] bytes = s.toString().trim().getBytes("utf-8");
                if (bytes.length > maxlength*3) {
                    int pos = s.length() - 1;
                    s.delete(pos, pos + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s.toString().trim().length() == 0) {
                if (!canNull) {
                    setFalse(true);
                } else {
                    setTrue();
                }
            } else {
                if (StringUtils.isName(s.toString().trim())) {
                    setTrue();
                } else {
                    setFalse(false);
                }
            }
        }
    };

    public void setTextVisible(int visible) {
        textView.setVisibility(visible);
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
        textView.setText(tipText);
    }

    public void setTrue() {
        right = true;
        textView.setVisibility(View.GONE);
        line.setBackgroundColor(lineColor);
    }

    public void setFalse(boolean isNull) {
        right = false;
        if (isNull) {
            textView.setText(nullTipText);
        } else {
            textView.setText(tipText);
        }
        textView.setVisibility(View.VISIBLE);
        line.setBackgroundColor(lineTipColor);
    }

    public void setInputType(int inputType) {
        editText.setInputType(inputType);
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public String getText() {
        return editText.getText().toString().trim();
    }

    public View getLine() {
        return line;
    }

    public void setLine(View line) {
        this.line = line;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
