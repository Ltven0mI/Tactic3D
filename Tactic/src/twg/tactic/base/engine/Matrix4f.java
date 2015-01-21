package twg.tactic.base.engine;

public class Matrix4f {
	
	private float[][] m;
	
	public float[][] GetM() { return m; }
	
	public void SetM(float[][] m) { this.m = m; }
	
	public Matrix4f() {
		m = new float[4][4];
	}
	
	public Matrix4f InitIdentity() {
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		return this;
	}
	
	public Matrix4f Mul(Matrix4f r) {
		Matrix4f result = new Matrix4f();
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				r.Set(i, j, m[i][0] * r.Get(0, j) +
							m[i][1] * r.Get(1, j) +
							m[i][2] * r.Get(2, j) +
							m[i][3] * r.Get(3, j));
			}
		}
		return result;
	}
	
	public float Get(int x, int y) {
		return m[x][y];
	}
	
	public void Set(int x, int y, float val) {
		m[x][y] = val;
	}
	
}
