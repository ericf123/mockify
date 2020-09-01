package com.ericf123.mockify;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ProcessTextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // extract relevant data from calling intent
        CharSequence inputText = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean isReadOnly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        if (!isReadOnly) {
            // prepare input string and string builder
            String inputString = inputText.toString().toLowerCase();
            StringBuilder outputStringBuilder = new StringBuilder(inputString.length());

            // loop over input and capitalize every other character, appending to string builder
            // we only toggle the capitalization if the current character is a letter
            boolean caps = false;
            for (int i = 0; i < inputString.length(); i++) {
                char currChar = inputString.charAt(i);

                if (caps) {
                    outputStringBuilder.append(Character.toUpperCase(currChar));
                } else {
                    outputStringBuilder.append(currChar);
                }

                // toggle the capitalization, but only if it's a letter
                caps ^= Character.isLetter(currChar);
            }

            // replace the text with the mocking version
            Intent replaceTextIntent = new Intent();
            replaceTextIntent.putExtra(Intent.EXTRA_PROCESS_TEXT, outputStringBuilder.toString());
            setResult(RESULT_OK, replaceTextIntent);
        } else {
            // if it's readonly we can't really do anything
            setResult(RESULT_CANCELED);
        }

        finish();
    }
}