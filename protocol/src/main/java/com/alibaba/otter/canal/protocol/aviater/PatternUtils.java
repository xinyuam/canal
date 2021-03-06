package com.alibaba.otter.canal.protocol.aviater;

import java.util.Map;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MigrateMap;

public class PatternUtils {
    @SuppressWarnings("deprecation")
    private static Map<String, Pattern> patterns = MigrateMap.makeComputingMap(new MapMaker().softValues(),
        new Function<String, Pattern>() {

            public Pattern apply(String pattern) {
                try {
                    PatternCompiler pc = new Perl5Compiler();
                    return pc.compile(pattern,
                        Perl5Compiler.CASE_INSENSITIVE_MASK | Perl5Compiler.READ_ONLY_MASK
                                               | Perl5Compiler.SINGLELINE_MASK);
                } catch (MalformedPatternException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    public static Pattern getPattern(String pattern) {
        return patterns.get(pattern);
    }

    public static void clear() {
        patterns.clear();
    }
}
