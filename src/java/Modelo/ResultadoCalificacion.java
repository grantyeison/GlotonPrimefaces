package Modelo;

/**
 *
 * @author jalbersson.plazas
 */
public class ResultadoCalificacion 
{
    Calificacion calificacion;
    float promedioCalificacion;
    int totalCalificaciones;

    public ResultadoCalificacion() 
    {
    }

    public Calificacion getCalificacion() 
    {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) 
    {
        this.calificacion = calificacion;
    }

    public float getPromedioCalificacion() 
    {
        return promedioCalificacion;
    }

    public void setPromedioCalificacion(float promedioCalificacion) 
    {
        this.promedioCalificacion = promedioCalificacion;
    }

    public int getTotalCalificaciones() 
    {
        return totalCalificaciones;
    }

    public void setTotalCalificaciones(int totalCalificaciones) 
    {
        this.totalCalificaciones = totalCalificaciones;
    }
    
}
