package xyz.tcbuildmc.common.util.simpletools.container;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<A, B> {
    private A a;
    private B b;
}
