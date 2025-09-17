package pratique_PROGRAMACAO_DE_SOLUÇÕES_COMPUTACIONAIS;

import java.io.Serializable;

public enum Categoria implements Serializable {
    FESTA,
    ESPORTE,
    SHOW,
    CULTURA;
    
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}