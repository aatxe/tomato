/*
 	OrpheusMS: MapleStory Private Server based on OdinMS
    Copyright (C) 2012 Aaron Weiss <aaron@deviant-core.net>
    				Patrick Huy <patrick.huy@frz.cc>
					Matthias Butz <matze@odinms.de>
					Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tools.data.input;

import java.io.IOException;
import java.io.RandomAccessFile;
import tools.ConsoleOutput;

public class RandomAccessByteStream implements SeekableInputStreamBytestream {
	private RandomAccessFile raf;
	private long bytesRead = 0;

	public RandomAccessByteStream(RandomAccessFile raf) {
		super();
		this.raf = raf;
	}

	@Override
	public int readByte() {
		try {
			int tmp = raf.read();
			if (tmp == -1) {
				throw new RuntimeException("EOF");
			}
			bytesRead++;
			return tmp;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void seek(long offset) throws IOException {
		raf.seek(offset);
	}

	@Override
	public long getPosition() throws IOException {
		return raf.getFilePointer();
	}

	@Override
	public long getBytesRead() {
		return bytesRead;
	}

	@Override
	public long available() {
		try {
			return raf.length() - raf.getFilePointer();
		} catch (IOException e) {
			ConsoleOutput.print("Error: " + e);
			return -1;
		}
	}
}
